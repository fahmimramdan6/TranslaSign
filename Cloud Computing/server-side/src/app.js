require('dotenv').config();
const express = require('express');
const translateRouter = require('./controllers/translateController');
const errorHandler = require('./utils/errorHandler');

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use('/translate', translateRouter);

app.use(errorHandler);

module.exports = app;
