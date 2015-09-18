var express = require('express');
var bodyParser = require('body-parser');
var routes = require('./routes/api'); 
var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use('/api', routes); //This is our route middleware

module.exports = app;