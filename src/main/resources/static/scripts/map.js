document.addEventListener('DOMContentLoaded', () => {

    let Map, objectManager, pointsArr;

    let obj = {},
        namePointsArr = []


    function init(){
        Map = new ymaps.Map("map", {
                center: [54.73136947666353,55.970855740234335],
                zoom: 7,
                type: 'yandex#hybrid',
                controls: ['rulerControl', 'zoomControl', 'fullscreenControl']
            },
            {
                // При сложных перестроениях можно выставить автоматическое
                // обновление карты при изменении размеров контейнера.
                // При простых изменениях размера контейнера рекомендуется обновлять карту программно.
                // autoFitToViewport: 'always'
                // searchControlProvider: 'yandex#search'
            }),
            objectManager = new ymaps.ObjectManager({
                // Чтобы метки начали кластеризоваться, выставляем опцию.
                clusterize: true,
                geoObjectOpenBalloonOnClick: true,
                clusterOpenBalloonOnClick: false
            });

        Map.geoObjects.add(objectManager);


        console.log(namePointsArr);



        function CustomSearchProvider(points) {
            this.points = points;
        }

// Провайдер ищет по полю text стандартным методом String.ptototype.indexOf.
        CustomSearchProvider.prototype.geocode = function (request, options) {
            var deferred = new ymaps.vow.defer(),
                geoObjects = new ymaps.GeoObjectCollection();

            var points = [];

            // Ищет в свойстве text каждого элемента массива.
            for (var i = 0, l = this.points.length; i < l; i++) {
                let point = this.points[i];
                console.log('search');
                if (point.properties.hintContent.toLowerCase().indexOf(request.toLowerCase()) != -1) {
                    points.push(point);
                }
            }

            // Добавляет точки в результирующую коллекцию.
            for (var i = 0, l = points.length; i < l; i++) {
                var point = points[i],
                    coords = point.geometry.coordinates,
                    text = point.properties.hintContent;

                geoObjects.add(new ymaps.Placemark(coords, {
                    name: text,
                    description: '',                        //Заглушка для описания, по идее должно быть месторождение
                    balloonContentBody: '<p>' + text + '</p>',
                    boundedBy: [coords, coords]
                }));
            }

            deferred.resolve({
                // Геообъекты поисковой выдачи.
                geoObjects: geoObjects,
                // Метаинформация ответа.
                metaData: {
                    geocoder: {
                        // Строка обработанного запроса.
                        request: request,
                        // Количество найденных результатов.
                        found: geoObjects.getLength()
                    }
                }
            });

            // Возвращает объект-обещание.
            return deferred.promise();
        };



        $.ajax({
            url: '../testjsons/push.json'
        }).done(function(data) {
            objectManager.add(data);
            console.log(data.features);
            namePointsArr = data.features

            let bounds = objectManager.getBounds();
            console.log(bounds);
            bounds = [bounds[0][0] + (bounds[1][0] - bounds[0][0])/2,
                bounds[0][1] + (bounds[1][1] - bounds[0][1])/2]

            console.log(bounds)
            Map.setCenter(bounds, 8, {
                checkZoomRange: true
            });

            // Map.geoObjects.add(new ymaps.Placemark(bounds, {
            //     balloonContent: '<strong>центр</strong>'
            // }, {
            //     preset: 'islands#icon',
            //     iconColor: 'red'
            // }))

            // Добавляем контрол в верхний левый угол,
            let mySearchControl = new ymaps.control.SearchControl({
                options: {
                    // Заменяем стандартный провайдер данных (геокодер) нашим собственным.
                    provider: new CustomSearchProvider(namePointsArr),
                    float: "left",
                    // Не будем показывать еще одну метку при выборе результата поиска,
                    // т.к. метки коллекции myCollection уже добавлены на карту.
                    noPlacemark: true,
                    resultsPerPage: 5
                }});

            Map.controls.add(mySearchControl);
            console.log(mySearchControl);
        });


        function loadBalloonData (id) {
            let dataDeferred = ymaps.vow.defer(),
                hint = objectManager.objects.getById(id).properties.hintContent,
                keys = {
                    field: 'Месторождение',
                    area: 'Площадь',
                    workshop: 'ЦЕХ',
                    agzu: 'АГЗУ'
                }


            function resolveData () {
                let infoBlock = `<div class="preInfoField">&#9679 Точка ${hint}</div><ul class="pointInfoList">`
                fetch(`../testjsons/getDiscription.json`)
                    .then(data => data.json())
                    .then(data => {
                        for(let key in data){
                            console.log(data[key])
                            if(data[key]){
                                infoBlock += `<li>${keys[key]}:<br>${data[key]}</li><hr>`
                            }
                        }
                        infoBlock += '</ul>'

                        dataDeferred.resolve(infoBlock);
                    })
            }
            // window.setTimeout(resolveData, 1000);
            resolveData()
            return dataDeferred.promise();
        }

        function hasBalloonData (objectId) {
            return objectManager.objects.getById(objectId).properties.balloonContent;
        }

        objectManager.objects.events.add('click', function (e) {
            var objectId = e.get('objectId'),
                obj = objectManager.objects.getById(objectId);
            console.log(e.get('objectId'));
            if (hasBalloonData(objectId)) {
                objectManager.objects.balloon.open(objectId);
            } else {
                obj.properties.balloonContent = "Идет загрузка данных...";
                objectManager.objects.balloon.open(objectId);

                loadBalloonData(objectId).then(function (data) {
                    obj.properties.balloonContent = data;
                    objectManager.objects.balloon.setData(obj);
                });
            }
        });
    }

    ymaps.ready(init);


    let closeSBBtn = document.querySelector(".expandSidebarBtn")
    let sideBar = document.querySelector(".sidebar")
    let main = document.querySelector(".MapWrapper")

    closeSBBtn.onclick = function(){
        sideBar.classList.toggle("active");
        main.classList.toggle("active");
        Map.container.fitToViewport();
    }
});




