package uppu.model;

class QuadrupleTest {

    public static void main(String[] args) {
        Quadruple q = Quadruple.create();
        q.setX(Color.YELLOW, 98);
        System.out.println(q.getX(Color.YELLOW));
        q.setX(Color.YELLOW, 97);
        System.out.println(q.getX(Color.YELLOW));
    }
}