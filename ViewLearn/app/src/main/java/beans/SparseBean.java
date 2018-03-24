package beans;

/**
 * Created by taow on 2017/8/9.
 */

public class SparseBean {
    int a;
    String b;
    public SparseBean(int ma,String mb){
        a = ma;
        b = mb;
    }

    @Override
    public String toString() {
        return "SparseBean{" +
                "a=" + a +
                ", b='" + b + '\'' +
                '}';
    }
}
