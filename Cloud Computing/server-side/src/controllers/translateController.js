const express = require('express');
const multer = require('multer');
const videoProcessing = require('../services/videoProcessing');

const router = express.Router();
const upload = multer();

router.post('/', upload.single('video'), async (req, res, next) => {
  try {
    const videoBuffer = req.file.buffer;

    const transcript = await videoProcessing.processVideo(videoBuffer);

    res.status(200).json({ success: transcript });
  } catch (error) {
    next(error);
  }
});

module.exports = router;
