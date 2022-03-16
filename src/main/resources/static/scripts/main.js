

    const uploadFile = document.getElementById("upload_file");
    const uploadBtn = document.getElementById("upload_btn");
    const uploadText = document.getElementById("upload_text");

    uploadBtn.addEventListener("click", function () {
        uploadFile.click();
    });

    uploadFile.addEventListener("change", function() {

        console.log(uploadFile.value)
        console.log(uploadText.value)
        console.log(uploadFile.value)

        if(uploadFile.value) {
            uploadText.innerHTML = uploadFile.value;
        } else {
            uploadText.innerHTML = "Файл не загружен";
        }
    });

    const uploadFile2 = document.getElementById("upload_file2");
    const uploadBtn2 = document.getElementById("upload_btn2");
    const uploadText2 = document.getElementById("upload_text2");

    uploadBtn2.addEventListener("click", function () {
        uploadFile2.click();
    });

    uploadFile2.addEventListener("change", function() {

        console.log(uploadFile2.value)
        console.log(uploadText2.value)
        console.log(uploadFile2.value)

        if(uploadFile2.value) {
            uploadText2.innerHTML = uploadFile2.value;
        } else {
            uploadText2.innerHTML = "Файл не загружен";
        }
    });
