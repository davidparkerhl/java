import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Vector;


// 调用PrintWriter的构造方法可能会抛出某种I/O异常，Java强制编写代码处理这类异常
// 为了简单起见，只要在方法头中声明 throws Exception 即可

public class FileIo
{
	public static void main(String[] args) throws IOException
  {
//  名字 种类 颜色 年龄 性别 服务 状态 主人 是否会员
		File file = new File("pets.txt");

    Scanner input = new Scanner(file);
		int rowNum=Integer.parseInt(input.next());
		int colNum=Integer.parseInt(input.next());

    String[] columnNames=new String[colNum];
		for(int i=0;i<colNum;i++)
    {
      columnNames[i]=input.next();
		}
    // System.out.println(columnNames[0]);
		Object[][] petData=new Object[rowNum+1][colNum+1];

		for(int i=0;i<rowNum;i++)
			for(int j=0;j<colNum;j++)
			{
				petData[i][j]=input.next();
			}
		// System.out.println(petData[0][0]);
		// 没必要关闭输入文件，但这样做是一种释放资源的好方法
		input.close();

    /*
          String name = input.next();
    			String type = input.next();
    			String color = input.next();
          String age = input.next();
          String gender = input.next();
          String service = input.next();
          String status = input.next();
          String owner = input.next();
          String premium = input.next();
    */
    		//	System.out.println(name + " " + name + " " + name + " " + name + " " + name + " " + name + " " + name + " " + name + " " + );


			File fileout = new File("out.txt");

		// 如果不存在则创建一个新文件
		try (PrintWriter output = new PrintWriter(fileout);)
    {
			output.print(rowNum);
			output.print(" ");
			output.print(colNum);
			output.print(" ");

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
					output.print(petData[i][j]);
					output.print(' ');
				}
				output.print('\n');
			}
			// // 若没有try-with-resources结构则必须使用 close() 关闭文件，否则数据就不能正常地保存在文件中
			// output.close();
		}

	}

}
