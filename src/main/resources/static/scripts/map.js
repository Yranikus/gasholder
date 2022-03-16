document.addEventListener('DOMContentLoaded', () => {

    let Map, objectManager, pointsArr;

    let obj = {}


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
        // objectManager.add(obj);


        $.ajax({
            url: 'http://localhost:8082/rest/getpoints'
        }).done(function(data) {
            console.log(JSON.parse(data))
            console.log(data.features)
            objectManager.add(data);
        });




        let mySearchControl = new ymaps.control.SearchControl({
            options: {
                // Заменяем стандартный провайдер данных (геокодер) нашим собственным.
                provider: new CustomSearchProvider(pointsArr),
                float: "left",
                // Не будем показывать еще одну метку при выборе результата поиска,
                // т.к. метки коллекции myCollection уже добавлены на карту.
                noPlacemark: true,
                resultsPerPage: 5
            }});

        // Добавляем контрол в верхний правый угол,
        Map.controls.add(mySearchControl);


        function loadBalloonData () {
            var dataDeferred = ymaps.vow.defer();
            function resolveData () {
                dataDeferred.resolve('Данные балуна');
            }
            window.setTimeout(resolveData, 1000);
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


    // fetch('https://geocode-maps.yandex.ru/1.x/?format=json&apikey=74bf9661-b70c-4d9e-875c-fc4500cf3256=55.689941211,54.484870589&kind=hydro&results=1')
    //     .then(data => {
    //         console.log(data.json())
    //     })

    //aef71caa-e287-4cde-a13b-e6a23db70fd8
    //https://geocode-maps.yandex.ru/1.x/?format=json&apikey=aef71caa-e287-4cde-a13b-e6a23db70fd8&geocode=Москва, улица Новый Арбат, дом 24
        // .then(data => data.json())
        // .then(function (data) {
        //     data.Holes.forEach((item, key) => {
        //         namePointsArr.push(item.HoleNumber);
        //         coords = item.gps.split(',');
        //         coords[0] = +coords[0];
        //         coords[1] = +coords[1];
        //
        //         obj.features.push(
        //             {
        //                 type: "Feature",
        //                 id: key,
        //                 geometry: {
        //                     type: "Point",
        //                     coordinates: coords
        //                 },
        //                 properties: {
        //                     hintContent: item.HoleNumber
        //                 }
        //             }
        //         )
        //     })
        //     pointsArr = obj.features;
        //     console.log(pointsArr);
        //
        //
        //     console.log(JSON.stringify(obj));
        //
        //     return JSON.stringify(obj)
        // }).then(data => {
        //     obj = data;
        //     ymaps.ready(init);
        // })




    function CustomSearchProvider(points) {
        this.points = points;
    }

// Провайдер ищет по полю text стандартным методом String.ptototype.indexOf.
    CustomSearchProvider.prototype.geocode = function (request, options) {
        let deferred = new ymaps.vow.defer(),
            geoObjects = new ymaps.GeoObjectCollection(),
            // Сколько результатов нужно пропустить.
            offset = options.skip || 0,
            // Количество возвращаемых результатов.
            limit = options.results || 20;

        let points = [];
        // Ищем в свойстве text каждого элемента массива.
        for (let i = 0, l = this.points.length; i < l; i++) {
            let point = this.points[i];
            if (point.properties.hintContent.toLowerCase().indexOf(request.toLowerCase()) != -1) {
                points.push(point);
            }
        }
        // При формировании ответа можно учитывать offset и limit.
        points = points.splice(offset, limit);
        // Добавляем точки в результирующую коллекцию.
        for (let i = 0, l = points.length; i < l; i++) {
            let point = points[i],
                coords = point.geometry.coordinates,
                text = point.properties.hintContent;

            geoObjects.add(new ymaps.Placemark(coords, {
                name: text + ' name',
                description: text + ' description',
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
                    found: geoObjects.getLength(),
                    // Количество возвращенных результатов.
                    results: limit,
                    // Количество пропущенных результатов.
                    skip: offset
                }
            }
        });

        // Возвращаем объект-обещание.
        return deferred.promise();
    };






    let closeSBBtn = document.querySelector(".expandSidebarBtn")
    let sideBar = document.querySelector(".sidebar")
    let main = document.querySelector(".MapWrapper")

    closeSBBtn.onclick = function(){
        sideBar.classList.toggle("active");
        main.classList.toggle("active");
        Map.container.fitToViewport();
    }
});




