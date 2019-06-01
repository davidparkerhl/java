public class T extends JFrame {
  T() {
    this.setBounds(100, 100, 300, 200);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        exit();
      }
    });
  }
