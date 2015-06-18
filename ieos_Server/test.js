var httpreq = require('request');
var login ="http://localhost:8080/mobelogin";

httpreq.post(login,{form:{"name":"abc","password":"123"}},function(err,http_res,body){
	var jar =httpreq.jar();
	var cookie =httpreq.cookie(String(http_res.headers["set-cookie"]));
	var url='http://localhost:8080/mobevoice';
	jar.setCookie(cookie,url);
	httpreq({
		uri:url,
		method:'post',
		jar:jar,
		form:{speechresult:"stereo change channel 5"}
		},function(err,http_res,body){
		console.log(body);
	});
});

/*
var abc={"device_name":"tv","action":"on"};
var test= "http://140.112.42.154:5566";
var t="http://httpbin.org/post";

httpreq.post(t,abc,function(err,http_res,body){
	if(err)
		console.log(err);
	console.log(http_res);
});

*/