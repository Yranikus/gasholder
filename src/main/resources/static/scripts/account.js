document.addEventListener('DOMContentLoaded', () => {

    const getUrlUserInfo = 'http://37.230.112.84:80/rest/user';
          // getURLPoints = 'http://localhost:1223/rest/points';

    const infoBlock = document.querySelector('.userInfoBlock')


//Функции для сайдбара
    let closeSBBtn = document.querySelector(".expandSidebarBtn")
    let sideBar = document.querySelector(".sidebar")
    let main = document.querySelector(".userInfoWrapper")


    closeSBBtn.onclick = function () {
        sideBar.classList.toggle("active")
        main.classList.toggle("active")
    }


//Добавление спинера загрузки, пока не придет ответ от сервера для двух полей
    function ldMessage() {
        const loadingMessage = document.createElement("img");
        loadingMessage.src = 'svg/spinner.svg';
        loadingMessage.style.cssText = `display: block; margin: auto auto;`;
        return loadingMessage;
    }

    const loadingMessage = ldMessage()
    infoBlock.insertAdjacentElement('afterbegin', loadingMessage);

    // const pointList = document.querySelector('.pointList'),
    //       loadingListElement = document.createElement('li');
    // loadingListElement.classList.add('pointInfoField');
    // loadingListElement.insertAdjacentElement("afterbegin", ldMessage());
    // pointList.insertAdjacentElement('afterbegin', loadingListElement);


//Функции заполения полей информации о пользователе в зависимости от ответа от сервера
    function fillInfoFields(data) {
        loadingMessage.remove()

        // const fioField = document.querySelector('#fio'),
           const departmentField = document.querySelector('#division');
            // districtField = document.querySelector('#district')

        // fioField.innerText = `${data.login}`;
        departmentField.innerText = data.department;
    }

    function errorInfoResponse() {
        loadingMessage.remove()

        let errInfoDiv = document.createElement('div')

        errInfoDiv.classList.add('errBlock')
        errInfoDiv.innerHTML = "<div>Ошибка запроса.<br>Перезагрузите страницу или попробуйте позже</div>"

        infoBlock.insertAdjacentElement('afterbegin', errInfoDiv);
    }


//Обработчики ответа fetch от сервера
    function responseStatus(response) {
        if (response.status >= 200 || response.status < 300) {
            return Promise.resolve(response)
        } else {
            return Promise.reject(new Error(response.statusText))
        }
    }


//Info fetch GET и заполнение полей
    fetch(getUrlUserInfo)
        .then(responseStatus)
        .then(data => data.json())
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            fillInfoFields(data);
        }).catch(function (error) {
        errorInfoResponse()
        console.log('Request failed', error);
    });

//Класс одного элемента списка
//     class pointCard {
//         constructor(pointObj, parentSelector, id, ...classes) {
//             this.holeNumber = pointObj.HoleNumber;
//             this.field = pointObj.Field;
//             this.area = pointObj.Area;
//             this.agzu = pointObj.AGZU;
//             this.workshop = pointObj.Workshop;
//             this.parent = document.querySelector(parentSelector);
//             this.id = "pointInfo" + id
//         }
//
//         render() {
//             this.parent.insertAdjacentHTML("beforeend",
//                 `<li class="preInfoField">
//                         &#9679 Точка ${this.holeNumber}
//                     </li>
//                     <li class="pointInfoField">
//                         <ul class="poinInfoList" id="${this.id}">
//                         </ul>
//                     </li>
//                     `);
//
//             let pointInfoList = document.querySelector(`#${this.id}`);
//
//             if (this.field !== undefined) {
//                 pointInfoList.insertAdjacentHTML('beforeend',
//                     `<li class>
//                             Месторождение:<br>${this.field}
//                         </li>`)
//             }
//
//             if (this.area !== undefined) {
//                 pointInfoList.insertAdjacentHTML('beforeend',
//                     `<li>
//                             Площадь:<br>${this.area}
//                         </li>`)
//             }
//
//             if (this.agzu !== undefined) {
//                 pointInfoList.insertAdjacentHTML('beforeend',
//                     `<li>
//                             АГЗУ:<br>${this.agzu}
//                         </li>`)
//             }
//
//             if (this.workshop !== undefined) {
//                 pointInfoList.insertAdjacentHTML('beforeend',
//                     `<li>
//                             Цех:<br>${this.workshop}
//                         </li>`)
//             }
//         }
//     }

//Функции заполения списка точек в зависимости от ответа от сервера
//     function fillPointList(data) {
//
//         data.forEach((item, key) => {
//             new pointCard(item, '.pointList', key).render()
//         });
//
//     }

//Point fetch get и заполнение списка точек
//     fetch(getURLPoints)
//         .then(data => data.json())
//         .then(function (data) {
//             loadingListElement.remove()
//             fillPointList(data);
//             console.log('Request succeeded with JSON response', data);
//         })
//         .catch(function (error) {
//             loadingListElement.remove()
//             console.log('Request failed', error);
//         });

});
