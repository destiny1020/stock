var mysql = require('mysql');
var fs = require('fs');

var dbConfig = JSON.parse(fs.readFileSync('./db.json'));

var connection = mysql.createConnection({
  host     : dbConfig.host,
  user     : dbConfig.user,
  password : dbConfig.password,
  database : dbConfig.database
});

connection.connect();

connection.query('SELECT * from data_daily where code = "150019"', function(err, rows, fields) {
  if (err) throw err;

  console.log(rows[0]);
});

connection.end();