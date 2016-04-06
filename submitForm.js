function createXHR() {
        if (typeof XMLHttpRequest != "undefined") {
            return new XMLHttpRequest();
        } else if (typeof ActiveXObject != "undefined") {
            if (typeof arguments.callee.activeXString != "string") {
                var versions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp"];
                for (var i = 0, len = versions.length; i < len; i++) {
                    try {
                        var xhr = new ActiveXObject(versions[i]);
                        arguments.callee.activeXString = versions[i];
                        return xhr;
                    } catch (ex) {
                        //Ìø¹ý
                    }
                }
            }
            return new ActiveXObject(arguments.callee.activeXString);
        } else {
            throw new Error("NO XHR object available.")
        }
    }

    function submitData() {
        var xhr = createXHR();
        alert("request created!");
        xhr.open("post", "Upload", true);
        alert(xhr.status);
       /*
        xhr.onreadystatechange = function (event) {
            if (xhr.readyState == 4) {
                if ((xhr.status >= 200 && xhr.status < 300) || xhr.status == 304) {
                    alert(xhr.responseText);
                } else {
                    alert("Request was unsuccessful: " + xhr.status);
                }
            }
        };
*/
  
        alert("opended!");
        var form = document.getElementById("uploadFileForm");
        
        xhr.send(new FormData(form));
    }