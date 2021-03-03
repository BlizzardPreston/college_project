window.ajax = function (param) {    //Window 对象表示浏览器中打开的窗口。
    (function (param) {             //param() 方法创建数组或对象的序列化表示。
        //该序列化值可在进行 AJAX 请求时在 URL 查询字符串中使用。
        var
            config = {
                type: 'GET',    //数据类型
                url: '',        //路径
                data: null,     //数据
                success: function () {
                }
            },
            domainName = 'http://niumo.365hy.com/',
            requestMode = {
                POST: function () {     //带参数请求浏览器看不到
                    var req = createXMLHTTPRequest();
                    //在使用XMLHttpRequest对象发送请求和处理响应之前，必须先使用javascript创建一个XMLHttpRequest对象。
                    if (req) {
                        req.open("POST", config.url, true);
                        //true异步  open("method","url")，建立对服务器的调用，method参数可以是GET,POST或PUT。
                        //url参数可以是相对URL或绝对URL。这个方法还包括3个可选参数。
                        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8;");
                        //setRequestHeader("header","value")，把指定首部设置为所提供的值。在设置任何首部之前必须先调用open().
                        if (!!config.data && config.data.constructor === Object) {
                            //constructor 属性返回对创建此对象的数组函数的引用。
                            var data = "";
                            Object.keys(config.data).forEach(function (key) {
                                //Object.keys(config.data)查询config.data内容
                                data += key + "=" + config.data[key] + "&";
                            })
                            config.data = data.substr(0, data.length - 1);
                        }
                        req.send(config.data);  //send(content)，向服务器发送请求
                        req.onreadystatechange = function () {
                            onReadyStateChange(req);
                        };
                    }
                },
                GET: function () {    //带参数请求浏览器看的到
                    var req = createXMLHTTPRequest();
                    if (req) {
                        if (!!config.data) {
                            if (config.data.constructor === Object) {
                                var data = "";
                                Object.keys(config.data).forEach(function (key) {
                                    data += key + "=" + config.data[key] + "&";
                                })
                                config.data = data.substr(0, data.length - 1);
                            }
                            config.url += "?" + config.data;
                        }
                        req.open("GET", config.url, true);
                        req.onreadystatechange = function () {
                            onReadyStateChange(req);
                        }
                        req.send(null);
                    }
                },
                UPLOAD: function () {        //上传
                    var req = createXMLHTTPRequest();
                    if (req) {
                        req.open("POST", config.url, true);
                        //req.setRequestHeader("Content-Type", "multipart/form-data");
                        if (!!config.data) {
                            if (config.data.constructor === Object) {
                                var data = new FormData();
                                Object.keys(config.data).forEach(function (key) {
                                    var val = config.data[key];
                                    if (val.constructor === Array || val.constructor == FileList) {
                                        for (var i = 0; i < val.length; i++) {
                                            data.append(key, val[i]);
                                        }
                                    } else {
                                        data.append(key, val);
                                    }
                                })
                                config.data = data;
                            }
                        }
                        req.send(config.data);
                        req.onreadystatechange = function () {
                            onReadyStateChange(req);
                        };
                    }
                }
            }

        function Config() {
            this.type = "GET";
            this.url = "";
            this.data = "";
            this.success = function () {

            }
        }

        function onReadyStateChange(req) {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    var res = req.responseText;
                    if (isJSON(req.responseText)) {
                        res = JSON.parse(res);
                    }
                    if (res.code == 1) {
                        config.success(res);
                    } else if (res.code == 2) {
                        errMsg(res.msg);
                    }
                } else {
                    errMsg("请求失败：（状态码：" + req.status + ")");
                }
            }
        }

        function isJSON(str) {
            if (typeof str == 'string') {
                try {
                    var obj = JSON.parse(str);
                    if (typeof obj == 'object' && obj) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (e) {
                    console.log('error：' + str + '!!!' + e);
                    return false;
                }
            }
        }

        function errMsg(text) {
            if (!!window.layer)window.layer.msg(text, {icon: 2});
            console.log(text);
        }

        function createXMLHTTPRequest() {
            var xmlHttpRequest;
            if (window.XMLHttpRequest) {
                xmlHttpRequest = new XMLHttpRequest();
                if (xmlHttpRequest.overrideMimeType) {
                    xmlHttpRequest.overrideMimeType("text/xml");
                }
            } else if (window.ActiveXObject) {
                var activexName = ["MSXML2.XMLHTTP", "Microsoft.XMLHTTP"];
                for (var i = 0; i < activexName.length; i++) {
                    try {
                        xmlHttpRequest = new ActiveXObject(activexName[i]);
                        if (xmlHttpRequest) {
                            break;
                        }
                    } catch (e) {
                    }
                }
            }
            return xmlHttpRequest;
        }

        function ajax() {
            Object.assign(config, param);
            if (!config.url) return errMsg("url:" + param.url);
            if (config.url.indexOf("http://") == -1 && config.url.indexOf("https://") == -1) config.url = domainName + config.url;
            config.type = config.type.toLocaleUpperCase();
            requestMode[config.type]();
        }

        ajax();
    })(param);
}
