import tensorflow as tf
import json

# Muat model dari file H5
model = tf.keras.models.load_model('ASL_MOBILENETV3SMALL.h5')

# Dapatkan arsitektur model dalam format JSON
model_json = model.to_json()

# Simpan arsitektur model JSON ke file
with open('ASL_MOBILENETV3SMALL.json', 'w') as json_file:
    json_file.write(model_json)