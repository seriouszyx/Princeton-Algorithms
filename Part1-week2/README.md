## 栈和队列

* 栈：先进（入栈）后出（出栈）
* 队列：先进（入队）先出（出队）

在实现之前，老师提到了模块化的思想，它使得我们能够用模块式可复用的算法与数据结构的库来构建更复杂的算法和数据结构，也使我们能在必要的时候更关注效率。这门课也会严格遵守这种风格。

### 栈

假设我们有一个字符串的集合，我们想要实现对字符串集合的存储、定期取出并返回最后添加的字符串、检查集合是否为空。

下面是 API：

![1](imgs/1.png)

#### 链表实现

课程中有关链表的操作都使用内部类定义节点元素：

```java
private class Node {
    String item;
    Node next;
}
```

API 实现：

```java
public class LinkedStackOfStrings {
    private Node first = null;
    
    private class Node {
        String item;
        Node next;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public void push (String item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }
    
    public String pop() {
        String item = first.item;
        first = first.next;
        return item;
    }
}
```

上面的代码也体现了使用 Java 学习数据结构的优点，不需要考虑麻烦的指针，而且垃圾回收机制也避免了主动释放内存。

在实现中，每个操作的最坏时间需求都是常数的；在 Java 中，每个对象需要16字节的内存空间，在这里，内部类需要8字节，字符串和 Node 节点的引用也分别需要8字节，所以每个 Node 节点共需要40字节，当元素数量 N 很大时，40N 是对空间需求非常接近的估计。

#### 数组实现

```java
public class ResizingArrayStackOfStrings {
    private String[] s;
    private int N = 0;
    
    public FixedCapacityStackOfStrings(int capacity) {
        s = new String[capacity];
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public void push (String item) {
        if (N == s.length)
            resize(2 * s.length);
        s[N++] = item;
    }
    
    public String pop() {
        String item = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length / 4)
            resize(s.length / 2);
        return item;
    }
    
    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }
    
    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }
}
```

平均运行时间还是与常数成正比，只不过进行内存分配时，需要 O(N) 的复杂度。

![2](imgs/2.png)

内存用量在 8N 到 32N 之间。

#### 动态数组 vs. 链表


