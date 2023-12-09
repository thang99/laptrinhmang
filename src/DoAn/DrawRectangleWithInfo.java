import DoAn.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class DrawRectangleWithInfo extends JFrame {
    private BufferedImage image;

    public DrawRectangleWithInfo() {
        setTitle("Draw Rectangle with Info Example");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load your image (replace "path/to/your/image.jpg" with the actual path to your image)
        image = loadImage("C:\\Users\\HoangPhi\\Desktop\\dog.jpg");
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the image
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        // Draw rectangles on the image and display object info
        drawRectanglesWithInfo(g);
    }

    private void drawRectanglesWithInfo(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Example object data
        Item[] itemArray = {
                new Item("Bicycle", 0.9951438903808594, 0.1684103012084961, 0.22224272787570953, 0.740515410900116, 0.7335137277841568),
                new Item("Car", 0.97198974609375, 0.6046382784843445, 0.12708207964897156, 0.894552618265152, 0.2970140427350998)
        };

        // Draw rectangles and display object info
        for (Item item : itemArray) {
            drawRectangle(g2d, item.getXMin(), item.getYMin(), item.getXMax(), item.getYMax(), Color.RED);
            displayObjectInfo(g2d, item);
        }

        // Save the image with rectangles and info
        try {
            File output = new File("C:\\Users\\HoangPhi\\Desktop\\dog2.jpg");
            ImageIO.write(image, "jpg", output);
            System.out.println("Image with info saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawRectangle(Graphics2D g2d, double xMin, double yMin, double xMax, double yMax, Color color) {
        int x = (int) (xMin * getWidth());
        int y = (int) (yMin * getHeight());
        int rectWidth = (int) ((xMax - xMin) * getWidth());
        int rectHeight = (int) ((yMax - yMin) * getHeight());

        g2d.setColor(color);
        g2d.drawRect(x, y, rectWidth, rectHeight);
    }

    private void displayObjectInfo(Graphics2D g2d, Item items) {
        int x = (int) (items.getXMin() * getWidth());
        int y = (int) (items.getYMin() * getHeight());

        g2d.setColor(Color.WHITE);
        g2d.drawString("Label: " + items.getLabel(), x, y - 5);
        g2d.drawString("Confidence: " + items.getConfidence(), x, y - 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawRectangleWithInfo drawRectangleWithInfo = new DrawRectangleWithInfo();
            drawRectangleWithInfo.setVisible(true);
        });
    }
}
