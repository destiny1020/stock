var mysql = require('mysql');
var fs = require('fs');

var dbConfig = JSON.parse(fs.readFileSync('./db/db.json'));

var Conn = function() {
    var conn = mysql.createConnection({
      host     : dbConfig.host,
      user     : dbConfig.user,
      password : dbConfig.password,
      database : dbConfig.database
    });

    return conn;
}();

module.exports = Conn;