
//Using code from depth first search simple maze all the way from coding rooms
public interface StackInterface<E> {
    public void push(E o);
    public E peek();
    public E pop();
    public E get(int x);
    public int size();
    public boolean isEmpty();
    public void clear();
}
