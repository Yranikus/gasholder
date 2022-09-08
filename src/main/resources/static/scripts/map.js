document.addEventListener('DOMContentLoaded', () => {

    let Map, objectManager;

    let namePointsArr = [];

    function getCookie(name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }



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
        objectManager.clusters.options.set('preset', 'islands#blackClusterIcons');

        MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
            '<div>' +
            '<div class="pointName"> $[properties.iconContent]' +
            '</div>'+
            '</div>'
        )

        Map.events.add('boundschange', () => {
            const zoom = Map.getZoom();
            if(zoom >= 18){
                objectManager.options.set("clusterize", false);
            }
            else{
                objectManager.options.set("clusterize", true);
            }
        })


        objectManager.objects.options.set("iconContentLayout", MyIconContentLayout)
        objectManager.objects.options.set("iconContentOffset", [5, -15])

        // console.log(namePointsArr);

        function CustomSearchProvider(points) {
            this.points = points;
        }

// Провайдер ищет по полю text стандартным методом String.ptototype.indexOf.
        CustomSearchProvider.prototype.geocode = function (request, options) {

                var deferred = new ymaps.vow.defer(),
                    geoObjects = new ymaps.GeoObjectCollection();

                let searchManager = new ymaps.ObjectManager({
                    // Чтобы метки начали кластеризоваться, выставляем опцию.
                    clusterize: true,
                    geoObjectOpenBalloonOnClick: true,
                    clusterOpenBalloonOnClick: false
                });

                let res = $.ajax(`http://37.230.112.84:80/rest/search/${request}`, {
                    success: function (data){
                        var points = data.features

                        for (var i = 0, l = points.length; i < l; i++) {
                            var point = points[i],
                                coords = point.geometry.coordinates,
                                text = point.properties.hintContent;

                            const name = text.substring(text.indexOf(" "), text.indexOf("<br/>")),
                                area = text.substring(text.indexOf("Площадь:") + 8, text.indexOf("<br/>Цех: ")),
                                field = text.substring(text.indexOf("<br/>Месторождение: ") + 20, text.indexOf("<br/>Площадь: "))
                            workshopj = text.substring(text.indexOf("<br/>Цех: ") + 9, text.length);
                            myPoint = new ymaps.Placemark(coords, {
                                name: name,
                                description: `${area} пл.,` + "\n" +  ` ${field} н.м.р,` + `${workshopj}`,                        //Заглушка для описания, по идее должно быть месторождение
                                balloonContentBody: '<p>' + text + '</p>',
                                boundedBy: [coords, coords]
                            });
                            geoObjects.add(myPoint)
                        }

                        // console.log('geoObjects', geoObjects);
                        // console.log('geoObjects.lenght', geoObjects.getLength());
                        //
                        // console.log('searchManager.objects', searchManager.objects);

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
                    }
                });




                // Возвращает объект-обещание.
                return deferred.promise();



            // Ищет в свойстве text каждого элемента массива.
            // for (var i = 0, l = this.points.length; i < l; i++) {
            //     let point = this.points[i];
            //     let hintContent = point.properties.hintContent,
            //     pointName = hintContent.substring(hintContent.indexOf(" "), hintContent.indexOf("<br/>"));
            //     if(request.toLowerCase() !== ('id' || 'месторождение' || 'площадь' || ' ' || ':')) {
            //         if (pointName.toLowerCase().indexOf(request.toLowerCase()) === 1) {
            //             points.push(point);
            //         }
            //     }
            // }
            // Добавляет точки в результирующую коллекцию.



        };


        let workshop = getCookie('workshop');


        //Убрать

        // const point = {
        //     type: "FeatureCollection",
        //     features: [
        //   {
        //     type: "Feature",
        //     id: 0,
        //     geometry: {
        //       type: "Point",
        //       coordinates: [
        //         54.7362627463035,
        //         56.130677515985994
        //     ]
        //     },
        //     properties: {
        //       hintContent: `id: 1818<br/>Месторождение: какое-то<br/>Площадь: Какая-то`
        //     },
        //     options: {
        //         iconLayout: 'default#image',
        //         iconImageHref: '../static/img/oil.png',
        //         iconImageSize: [60, 45],
        //         iconImageOffset: [-18, -12]
        //     }
        //   }]
        // };
        //
        // objectManager.add(JSON.stringify(point));
        //
        // console.log(objectManager);

        //До сюда

        $.ajax({
            url: `http://37.230.112.84:80/rest/${workshop}`                     //допиши тута запрос на получение точек
        }).done(function(data) {
            objectManager.add(data);
            console.log(data);
            namePointsArr = data.features;

            let bounds = objectManager.getBounds();
            console.log(bounds);
            bounds = [bounds[0][0] + (bounds[1][0] - bounds[0][0])/2,
                bounds[0][1] + (bounds[1][1] - bounds[0][1])/2];

            console.log(bounds);
            Map.setCenter(bounds, 8, {
                checkZoomRange: true
            });
            

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
                pointCoords = objectManager.objects.getById(id).geometry.coordinates,
                keys = {
                    field: 'Месторождение',
                    area: 'Площадь',
                    workshop: 'ЦЕХ',
                    agzu: 'АГЗУ',
                    city: 'Город',
                    distance : 'Растояние',
                    direction: 'Направление',
                    reservior: 'Ближайший водоем',
                    reservior_distance : 'Растояние',
                    reservior_direction: 'Направление',
                }


            function resolveData () {
                let infoBlock = `<div class="preInfoField">&#9679 Скважина ${hint.substring(hint.indexOf(" "), hint.indexOf("<br/>"))}</div><ul class="pointInfoList">`
                let c = 0;
                fetch(`http://37.230.112.84:80/rest/getdiscription?id=${id}`)                                       //А тут дописать запрос на описание точки
                    .then(data => data.json())
                    .then(data => {
                        if (data['field'] !== "") {
                            infoBlock += `<li>${data['area']} пл.,<br/>${data['field']} н.м.р,<br/>${data['workshop']}</li><hr>`
                        }
                        else {
                            infoBlock += `<li>${data['area']} пл.,<br/> ${data['workshop']}</li><hr>`
                        }
                        infoBlock += `<li><img src="img/city.png">${data['city']}-${data['distance']}км-${data['direction']}</li><hr>`
                        infoBlock += `<li><img src="img/reservior.png">${data['reservior']}-${data['reservior_distance']}км-${data['reservior_direction']}</li><hr>`
                        infoBlock += `Нажмите, чтобы скопировать<br><button class="coordsField">${[pointCoords.join(' ')]}</button>`
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

    document.addEventListener('click', (e) => {

        if(e.target.classList.contains('coordsField')){
            var copyTextarea = document.createElement("textarea");
            copyTextarea.style.position = "fixed";
            copyTextarea.style.opacity = "0";

            console.log(e.target.innerText);
            copyTextarea.textContent = e.target.innerText;

            document.body.appendChild(copyTextarea);
            copyTextarea.select();
            document.execCommand("copy");
            document.body.removeChild(copyTextarea);

            let copyModal = document.querySelector(".copyModal");
            copyModal.classList.add("modalActive");

            setTimeout(()=>{
                copyModal.classList.remove("modalActive");
            }, 500)
        }
    
    });

    let closeSBBtn = document.querySelector(".expandSidebarBtn")
    let sideBar = document.querySelector(".sidebar")
    let main = document.querySelector(".MapWrapper")

    closeSBBtn.onclick = function(){
        sideBar.classList.toggle("active");
        main.classList.toggle("active");
        Map.container.fitToViewport();
    };
});




