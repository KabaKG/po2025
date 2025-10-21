//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CodingBat {


    public static String lastTwo(String str) {
        if (str.length() < 2) {
            return str;
        } else {
            String sub1 = str.substring(str.length() - 1);
            String sub2 = str.substring(str.length() - 2, str.length() - 1);
            String sub3 = str.substring(0, str.length() - 2);
            return sub3 + sub1 + sub2;
        }
    }

    public int[] middleWay(int[] a, int[] b) {
        int[] c = {0, 0};
        c[0] = a[1];
        c[1] = b[1];

        return c;
    }

    public boolean in1020(int a, int b) {
        if ((a >= 10 && a <= 20) || (b >= 10 && b <= 20)) {
            return true;
        } else {
            return false;
        }

    }

    void main() {
        in1020(2,3);
        middleWay(new int[]{1,2,3},new int[]{4,5,6});
        String str = lastTwo("ebebebbe");
        System.out.println(str);

    }
}



