var express = require('express');
var bodyParser = require('body-parser');
var routes = require('./routes/api'); 
var app = express();

//设置跨域访问  
app.all('*', function(req, res, next) {  
    res.header("Access-Control-Allow-Origin", "*");  
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");  
    res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");  
    res.header("X-Powered-By",' 3.2.1')  
    res.header("Content-Type", "application/json;charset=utf-8");  
    next();  
}); 

app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use('/api', routes); //This is our route middleware

module.exports = app;