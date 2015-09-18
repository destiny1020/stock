var Conn = require('../db/conn')
var express = require('express');
var router = express.Router();

router.route('/history').get(function(req, res) {
    var connection = Conn;

    connection.query('SELECT * from data_daily where code = "150019"', function(err, rows, fields) {
      if (err) return res.send(err);

      res.json(rows[0]);
    });
});

module.exports = router;