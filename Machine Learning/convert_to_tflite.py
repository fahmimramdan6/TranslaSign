import tensorflow as tf

# Muat model dari file H5
model = tf.keras.models.load_model('ASL_MOBILENETV3SMALL.h5')

# Buat converter untuk model
converter = tf.lite.TFLiteConverter.from_keras_model(model)

# (Opsional) Konfigurasi converter
# Misalnya, optimasi model untuk ukuran atau performa
converter.optimizations = [tf.lite.Optimize.DEFAULT]

# Konversi model ke format TFLite
tflite_model = converter.convert()

# Simpan model TFLite ke file
with open('model.tflite', 'wb') as tflite_file:
    tflite_file.write(tflite_model)
