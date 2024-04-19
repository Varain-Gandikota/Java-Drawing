import java.util.*;

//copied stack code from depth first search from coding rooms
public class MyStack <E> implements StackInterface <E>
{
    private ArrayList <E> data;
    public void clear()
    {data = new ArrayList<E>();}
    public MyStack()
    {
        data = new ArrayList<E>();
    }
    public void push(E item)
    {
        data.add(item);
    }
    public E peek()
    {
        if(data.size()==0)
            return null;
        else
            return data.get(data.size()-1);
    }
    public E pop()
    {
        if (data.size() == 0)
            return null;
        else
            return data.remove(data.size()-1);
    }
    public boolean isEmpty()
    {
        return data.size() == 0;
    }
    public int size() {return data.size();}
    public E get(int x)
    {
        return data.get(x);
    }
    public String toString()
    {
        String s = "[";
        for (int i = 0; i < data.size(); i++)
        {
            if (i != data.size() - 1)
                s += data.get(i) + ", ";
            else
                s += data.get(i) + "]";
        }
        return s;
    }
}

