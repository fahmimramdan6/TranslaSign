import tensorflow as tf

# Load .h5 model
model = tf.keras.models.load_model('VGG16NewModel.h5')

# create converter instance for the model
converter = tf.lite.TFLiteConverter.from_keras_model(model)

# (Optional) converter config
# For example, model size and performance optimization
converter.optimizations = [tf.lite.Optimize.DEFAULT]

# Model convertion to tflite format
tflite_model = converter.convert()

# Saving model in tflite format
with open('VGG16NewModel.tflite', 'wb') as tflite_file:
    tflite_file.write(tflite_model)
