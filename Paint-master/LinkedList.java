
import java.util.ArrayList;

public class LinkedList<T> {
    private int numberOfNodes = 0; 
    private ListNode<T> front = null;
    
    // Возвращает значение true, если в связанном списке нет узлов, или значение false в противном случае.
    public boolean isEmpty() {
        return (front == null);
    }
    
    // Удаляет все узлы в связанном списке.
    // Примечание: Объекты ListNode будут автоматически собираться JVM для сбора мусора.
    public void makeEmpty() {
        front = null;
        numberOfNodes = 0;
    }
    
    // Возвращает количество узлов в связанном списке
    public int size() {
        return numberOfNodes;
    }
    
    // Adds a node to the front of the linked list.
    public void addFront( T element ) {
        front = new ListNode<T>( element, front );
        numberOfNodes++;
    }
    
    // Returns a reference to the data in the first node, or null if the list is empty.
    public T peek() {
        if (isEmpty()) 
            return null;
        
        return front.getData();
    }

    //Удаляет узел из начала связанного списка (если таковой имеется).
    // Возвращает ссылку на данные в первом узле или null, если список пуст.
    @SuppressWarnings("unchecked")
    public T removeFront() {
        T tempData;
        
        if (isEmpty()) 
            return null;
        
        tempData = front.getData();
        front = front.getNext();
        numberOfNodes--;
        return tempData;
    }
    
    @SuppressWarnings("unchecked")
    public void removeEnd(T element) {
        ListNode<T> node=front;
        while(node.getNext() != null)
        {
            node = node.getNext();
        }
        node.setNext(new ListNode<T>((T)element, null));
    }
    
    // Возвращает массив, заполненный T объектами
    @SuppressWarnings("unchecked")
    public ArrayList<T> getArray() {
        
        ArrayList<T> shapeArray=new ArrayList<T>();
        
        ListNode<T> node=front;
        while (node!=null)
        {
            shapeArray.add(node.getData());
            node = node.getNext();
        }
        
        return shapeArray;
    }
}
