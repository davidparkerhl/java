import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;

class SFrame extends JFrame
{
  public SFrame(JTable table,Object[] columnNames)
  {
      setTitle("宠物店管理系统");
      addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          try
          {
            save(table,columnNames);
          }
          catch(Exception ee)
          {
            System.out.println("还真出exception了！");
          }
          setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
      });

  }
  private void save(JTable table,Object[] columnNames) throws IOException
  {
    File fileout = new File("pets.txt");
    try (PrintWriter output = new PrintWriter(fileout);)
    {
      int colNum=table.getColumnCount();
      int rowNum=table.getRowCount();
      output.print(rowNum);
      output.print(" ");
      output.print(colNum);
      output.println("");

      for(int i=0;i<colNum;i++)
      {
        output.print(columnNames[i]);
        output.print(" ");
      }
      output.println();
      for(int i=0;i<rowNum;i++)
      {
        for(int j=0;j<colNum;j++)
        {
          output.print(table.getModel().getValueAt(i,j));
          output.print(' ');
        }
        output.print('\n');
      }

    }

    setDefaultCloseOperation(EXIT_ON_CLOSE);


  }

}

public class Main {
    public static void main(String[] args) throws IOException
    {
///////////////////// 数据读取与保存
        File file = new File("pets.txt");
        Scanner input = new Scanner(file);

        int rowNum=Integer.parseInt(input.next());
        int colNum=Integer.parseInt(input.next());

        String[] columnNames=new String[colNum];
        Object[][] petData=new Object[rowNum][colNum];

        for(int i=0;i<colNum;i++)
          columnNames[i]=input.next();

        for(int i=0;i<rowNum;i++)
          for(int j=0;j<colNum;j++)
            petData[i][j]=input.next();

        input.close();


////////////     表单创建
        DefaultTableModel tableModel = new DefaultTableModel(petData,columnNames);
        JTable table=new JTable(tableModel);

        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
        table.setRowSorter(sorter);

///////////     表单展示设置
        // 设置表格内容颜色

        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.BLACK);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色

        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 第一列列宽设置为40
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(980, 400));

        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）



//////////   面板和功能按钮
        // 创建内容面板
        JPanel panel = new JPanel();


        // 名字 种类 颜色 年龄 性别 服务 状态 主人 是否会员
                JTextField[] tf=new JTextField[colNum];
                final int WIDTH=3;
                for(int i=0;i<colNum;i++)
                {
                  panel.add(new JLabel(columnNames[i]));
                  tf[i]=new JTextField("",WIDTH);
                  panel.add(tf[i]);
                }

                final JButton addButton = new JButton("添加");   //添加按钮
                addButton.addActionListener(new ActionListener()
                {//添加事件
                    public void actionPerformed(ActionEvent e)
                    {
                        String[] newData = new String[colNum];
                        for(int i=0;i<colNum;i++)
                          newData[i]=tf[i].getText();
                        tableModel.addRow(newData);  //添加一行
                        // int rowCount = table.getRowCount() +1;   //行数加上1
                    }
                });
                panel.add(addButton);

                final JButton delButton = new JButton("删除");
                delButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        int selectedRow = table.getSelectedRow();//获得选中行的索引
                        if(selectedRow!=-1)  //存在选中行
                        {
                            tableModel.removeRow(selectedRow);  //删除行
                        }
                    }
                });
                panel.add(delButton);




        // 添加 数据部分滚动面板 到 内容面板
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        JTextField filterText = new JTextField("",10);
        panel.add(new JLabel("搜索内容："));
        panel.add(filterText);

                JButton searchButton = new JButton("搜索");
                searchButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        String text = filterText.getText();
                        if (text.length() == 0)
                        {
                            sorter.setRowFilter(null);
                        }
                        else
                        {
                            sorter.setRowFilter(RowFilter.regexFilter(text));
                        }
                    }
                });
                panel.add(searchButton);

                JButton cancelButton = new JButton("取消");
                cancelButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        sorter.setRowFilter(null);
                    }
                });
                panel.add(cancelButton);



/////////   窗口处理
        // 设置 内容面板 到 窗口
        SFrame jf = new SFrame(table,columnNames);


        // jf.getContentPane()).setOpaque(false);
    	ImageIcon img = new ImageIcon("/home/david/Desktop/2.jpg");
    	JLabel background = new JLabel(img);
    	jf.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
      jf.getContentPane().setVisible(false);
    	background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());


        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setContentPane(panel);
        jf.pack();
        jf.setSize(1000 , 600);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);


    }
}

//名字 种类 颜色 年龄 性别 服务 状态 主人 是否会员
