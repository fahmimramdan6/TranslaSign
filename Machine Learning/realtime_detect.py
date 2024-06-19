import cv2
import numpy as np
import tensorflow as tf

# Load the trained model
model = tf.keras.models.load_model('VGG16NewModel/VGG16NewModel.h5')

# Parameters for the bounding box
bbox_start_point = None  # Initialize start point of bounding box
bbox_end_point = None  # Initialize end point of bounding box
drawing = False  # Flag to indicate if mouse is being used to draw
bbox_color = (255, 0, 0)  # Blue color in BGR
bbox_thickness = 2

# Mouse callback function to draw bounding box
def draw_bbox(event, x, y, flags, param):
    global bbox_start_point, bbox_end_point, drawing

    if event == cv2.EVENT_LBUTTONDOWN:
        drawing = True
        bbox_start_point = (x, y)
        bbox_end_point = (x, y)

    elif event == cv2.EVENT_MOUSEMOVE:
        if drawing:
            bbox_end_point = (x, y)

    elif event == cv2.EVENT_LBUTTONUP:
        drawing = False
        bbox_end_point = (x, y)

# Capture video from webcam
cap = cv2.VideoCapture(1)  # Use 0 for default webcam, change if you have multiple webcams

# Create a window and set the mouse callback function
cv2.namedWindow('Webcam Frame')
cv2.setMouseCallback('Webcam Frame', draw_bbox)

while True:
    ret, frame = cap.read()
    if not ret:
        break

    # Draw the bounding box on the frame
    if bbox_start_point and bbox_end_point:
        cv2.rectangle(frame, bbox_start_point, bbox_end_point, bbox_color, bbox_thickness)

    # Extract the region of interest (ROI) only if both start and end points are defined
    if bbox_start_point and bbox_end_point:
        x1, y1 = bbox_start_point
        x2, y2 = bbox_end_point
        roi = frame[min(y1, y2):max(y1, y2), min(x1, x2):max(x1, x2)]

        # Check if ROI is valid (non-empty)
        if roi.size > 0:
            # Preprocess the ROI for prediction
            roi_resized = cv2.resize(roi, (150, 150))  # Resize to the model input size
            roi_normalized = roi_resized - 0.75  # Reduce Brightness
            roi_normalized = roi_normalized/255. # Normalize
            roi_expanded = np.expand_dims(roi_normalized, axis=0)  # Expand dims to match the input shape of the model

            # Make prediction on the ROI
            predictions = model.predict(roi_expanded)
            predicted_class = np.argmax(predictions, axis=-1)[0]  # Get the predicted class
            num_to_alpha = {i: chr(65 + i) for i in range(26)}
            # Display the predicted class on the frame
            cv2.putText(frame, f'Huruf: {num_to_alpha[predicted_class]}', (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)

    # Show the frame
    cv2.imshow('Webcam Frame', frame)

    # Break the loop if 'q' is pressed
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# Release the capture and close windows
cap.release()
cv2.destroyAllWindows()
