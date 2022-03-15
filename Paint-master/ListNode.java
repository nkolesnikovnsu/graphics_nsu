/**
 * Jaired Jawed
 * Dec 10, 2017
 * ListNode.java
 */

public class ListNode<T> {
  private T data;
  private ListNode next;

  // Constructor: No reference to next node provided so make it null 
  public ListNode( T nodeData ) {
      this( nodeData, null);
  }
   
  // Constructor: Set data and reference to next node.
  public ListNode( T nodeData, ListNode nodeNext ) {
      data = nodeData;
      next = nodeNext;
  }
   
  // Средство доступа: Возвращает данные для текущего объекта ListNode
  public T getData() {
      return data;
  }
   
  // Средство доступа: Возвращает ссылку на следующий объект ListNode
  public ListNode getNext() {
      return next;
  }
   
  // Мутатор: Установка новых данных для текущего объекта ListNode
  public void setData( T newData ) {
      data = newData;
  }
   
   
  // Мутатор: Устанавливает новую ссылку на следующий объект узла
  public void setNext( ListNode newNext ) {
      next = newNext;
  }
}
