package query.nativesql;

public class NativeSQLMain {
    public static void main(String[] args) {
        NativeSQL nativeSQL = new NativeSQL();

        nativeSQL.nativeSQLSelect();

        nativeSQL.close();
    }
}
