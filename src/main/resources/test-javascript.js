//load("nashorn:mozilla_compat.js");
//importClass(java.lang.System);
var System = Java.type("java.lang.System");

System.out.println(request.getHeaders().toString());

load("classpath:WechatClient.js");

request.setBody([
    {
        "isbn": "isbn-2",
        "title": "<<钢铁是这样炼成的>>"
    }
]);
