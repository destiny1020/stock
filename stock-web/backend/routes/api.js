var Conn = require('../db/conn')
var express = require('express');
var router = express.Router();

// Weekly
router.route('/history/weekly/:code').get(function(req, res) {
    var connection = Conn;

    connection.query('SELECT * from data_week where code = "' + req.params.code + '" order by id desc', function(err, rows, fields) {
      if (err) return res.send(err);

      res.json(rows[0]);
    });
});

// Daily
router.route('/history/daily/:code').get(function(req, res) {
    var connection = Conn;

    connection.query('SELECT * from data_daily where code = "' + req.params.code + '" order by id desc', function(err, rows, fields) {
      if (err) return res.send(err);

      res.json(rows[0]);
    });
});

// 60 Minutes
router.route('/history/60min/:code').get(function(req, res) {
    var connection = Conn;

    connection.query('SELECT * from data_60min where code = "' + req.params.code + '" order by id desc', function(err, rows, fields) {
      if (err) return res.send(err);

      res.json(rows[0]);
    });
});

// 30 Minutes
router.route('/history/30min/:code').get(function(req, res) {
    var connection = Conn;

    connection.query('SELECT * from data_30min where code = "' + req.params.code + '" order by id desc', function(err, rows, fields) {
      if (err) return res.send(err);

      res.json(rows[0]);
    });
});

// 15 Minutes
router.route('/history/15min/:code').get(function(req, res) {
    var connection = Conn;

    connection.query('SELECT * from data_15min where code = "' + req.params.code + '" order by id desc', function(err, rows, fields) {
      if (err) return res.send(err);

      res.json(rows[0]);
    });
});

module.exports = router;