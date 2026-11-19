
// Import the Webcam library so Java can access the computer's camera
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

// Import ZXing libraries to detect and read barcodes
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

// Import Java classes needed to process images and configure the scanner
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class BarcodeScanner {

    public static void main(String[] args) {
        System.out.println("NEW VERSION OF BARCODE SCANNER RUNNING");

        // Get the computer's default webcam
        Webcam webcam = Webcam.getDefault();
        System.out.println("Available webcams:");

        for (Webcam camera : Webcam.getWebcams()) {
            System.out.println(camera.getName());
        }

        System.out.println("Selected webcam: " + webcam);
        System.out.println("Selected webcam: " + webcam);

        // Check whether a webcam is connected
        if (webcam == null) {
            System.out.println("No webcam detected!");
            return; // Stop the programme if no camera is found
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        // Open the webcam so it can start capturing images
        webcam.open();

        // Display the webcam feed in a window
        com.github.sarxos.webcam.WebcamPanel panel
                = new com.github.sarxos.webcam.WebcamPanel(webcam);

        javax.swing.JFrame window
                = new javax.swing.JFrame("Gym ID Barcode Scanner");

        window.add(panel);
        window.setSize(640, 480);
        window.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        window.setVisible(true);
        // Create a map to store the scanner's settings
        Map<DecodeHintType, Object> hints
                = new EnumMap<>(DecodeHintType.class);

        // Tell ZXing which barcode formats it should look for
        // We include several formats because we do not yet know
        // which barcode format is used on UWI ID cards
        hints.put(DecodeHintType.POSSIBLE_FORMATS,
                EnumSet.of(
                        BarcodeFormat.CODE_128,
                        BarcodeFormat.CODE_39,
                        BarcodeFormat.CODABAR,
                        BarcodeFormat.ITF,
                        BarcodeFormat.QR_CODE,
                        BarcodeFormat.EAN_13,
                        BarcodeFormat.EAN_8,
                        BarcodeFormat.UPC_A,
                        BarcodeFormat.UPC_E,
                        BarcodeFormat.PDF_417,
                        BarcodeFormat.DATA_MATRIX
                ));

        // Tell the scanner to try harder when reading barcodes
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        // Create the barcode reader and apply the settings
        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(hints);

        // Display instructions to the user
        System.out.println("Barcode scanner started!");
        System.out.println("Show a barcode to your webcam.");
        System.out.println("Close the programme to stop scanning.");

        try {

            // Keep scanning until the programme is stopped
            while (true) {

                // Capture an image from the webcam
                BufferedImage image = webcam.getImage();

                // Check whether an image was successfully captured
                if (image == null) {
                    continue; // Try again if no image was received
                }

                try {

                    // Convert the camera image into a format
                    // that ZXing can understand
                    LuminanceSource source
                            = new BufferedImageLuminanceSource(image);

                    // Prepare the image for barcode recognition
                    BinaryBitmap bitmap
                            = new BinaryBitmap(
                                    new HybridBinarizer(source));

                    // Attempt to find and read a barcode
                    Result result = reader.decodeWithState(bitmap);

                    // Display the information obtained from the barcode
                    System.out.println(
                            "Scanned ID: " + result.getText());

                    // Display the barcode format that was detected
                    System.out.println(
                            "Barcode type: "
                            + result.getBarcodeFormat());
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    System.out.println("Scan successful. Scanner stopped.");
                    break;// casue error 
                    // Pause briefly to prevent repeated rapid scans
                    Thread.sleep(1500);

                } catch (NotFoundException e) {

                    // No barcode was found in this camera image
                    // Continue scanning instead of stopping the programme
                } finally {

                    // Reset the reader so it can scan the next image
                    reader.reset();
                }
            }

        } catch (InterruptedException e) {

            // Restore the interrupted status if scanning is stopped
            Thread.currentThread().interrupt();

        } finally {

            // Close the webcam when the programme finishes
            webcam.close();

            System.out.println("Scanner stopped.");
        }
    }
}
