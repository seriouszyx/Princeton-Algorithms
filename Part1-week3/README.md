## 归并排序

归并排序的思想是把数组一分为二，然后再不断将小数组递归地一分为二下去，经过一系列排序再将它们合并起来。

```java
private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
    for (int k = lo; k <= hi; k++)
        aux[k] = a[k];
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
        if (i > mid)
            a[k] = aux[j++];
        else if (j > hi)
            a[k] = aux[i++];
        else if (less(aux[j], aux[i]))
            a[k] = aux[j++];
        else
            a[k] = aux[i++];
    }
}

private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
    if (hi <= lo)
        return;
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid+1, hi);
    if（!less(a[mid + 1], a[mid])）
        return;
    merge(a, aux, lo, mid, hi);
}

public static void sort(Comparable[] a) {
    Comparable[] aux = new Comparable[a.length];
    sort(a, aux, 0, a.length - 1);
}
```

归并排序可用于大量数据的排序，对于 million 和 billion 级别的数据，插入排序难以完成的任务归并排序可能几分钟就完成了。

对于 N 个元素，归并排序最多需要 NlgN 次比较和 6NlgN 次对数组的访问，并且要使用 N 个空间的辅助数组。

### 自底向上的归并排序

我们将归并排序的过程倒过来看，先将数组分为 2 个元素并将所有组排序，再分为 4 个元素并将所有组排序，... ，直到完成排序。

```java
public static void sort(Comparable[] a) {
    int N = a.length;
    aux = new Comparable[N];
    for (int sz = 1; sz < N; sz = sz + sz) 
        for (int lo = 0; lo < N - sz; lo += sz + sz)
            merge(a, lo, lo + sz - 1, Math.min(lo+sz+sz-1, N-1));
}
```

这是一个完全符合工业标准的代码，除了需要额外的存储空间。时间复杂度为 O(NlogN)。

### 排序规则

我们可以实现 Comparator 接口来为排序算法编写不同的排序规则，以插入排序为例：

```java
public static void sort(Object[] a, Comparator comparator) {
    int N = a.length;
    for (int i = 0; i < N; i++) 
        for (int j = i; j > 0 && less(comparator, a[j], a[j-1]); j--)
            exch(a, j, j - 1);
}

private static boolean less(Comparator c, Object v, Object w) {
    return c.compare(v, m) < 0;
}

private static void exch(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
}
```

```java
public class Student {
    public static final Comparator<Student> BY_NAME = new ByName();
    ...
    private static class ByName implements Comparator<Student> {
        public int compare(Student v, Student w)
            return v.name.compareTo(w.name);
    }
}
```

然后可以这样使用排序：

```java
Arrays.sort(a, Student.BY_NAME);
```

使用 Comparator 接口来替代 Comparable 接口的优点就是它支持待排序元素的多种排序规则。

## 快速排序

快速排序广泛运用于系统排序和其他应用中。它也是一个递归过程，与归并排序不同的是，它先进行操作然后再递归，而不是归并排序先进性递归然后再进行 merge。

算法的思想是先对数组随机打乱，然后每次都把第一个元素放到合适的位置，这个位置左边的元素都比它小，右边的元素都比它大，再将两侧的元素递归操作。

```java
private static int partition(Comparable[] a, int lo, int hi) {
    int i = lo, j = hi + 1;
    while (true) {
        while (less(a[++i], a[lo]))
            if (i == hi)
                break;
        while (less(a[lo], a[--j]))
            if (j == lo)
                break;
        if (i >= j)
            break;
        exch(a, i, j);
    }
    exch(a, lo, j);
    return j;
}

public static void sort(Comparable[] a) {
    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
}

private static void sort(Comparable[] a, int lo, int hi) {
    if (hi <= lo)
        return;
    int j = partition(a, lo, hi);
    sort(a, lo, j - 1);
    sort(a, j + 1, hi);
}
```

事实证明，快速排序比归并排序还要快，他最少需要 NlgN 次比较，最多需要 1/2 N^2 次。对于 N 个元素，快速排序平均需要 1.39NlgN 次比较，不过因为不需要过多的元素的移动，所以实际上它更快一些。其中，随机打乱是为了避免最坏的情况。

在空间使用上，它不需要额外的空间，所以是常数级别的。

### 案例

快速排序的一个案例是找一个数组中第 k 大的数。

```java
public static Comparable select(Comparable[] a, int k) {
    StdRandom.shuffle(a);
    int lo = 0, hi = a.length - 1;
    while (hi > lo) {
        int j = partition(a, lo, hi);
        if (j < k)
            lo = j + 1;
        else if (j > k)
            hi = j - 1;
        else
            return a[k];
    }
    return a[k];
}
```

这个解法的时间复杂度是线性的，不过有论文表明它的常数很大，所以在实践中效果不是特别好。

### 多个相同键值

很多时候排序的目的是将相同键值的元素排到一起，处理这种问题不同的排序方法的效率也不同。

归并排序需要 1/2 NlgN 至 NlgN 次比较。

快速排序将达到 N^2 除非 partition 过程停止的键值和结果键值相等，所以需要更好的算法实现，比较好的一种算法是 Dijkstra 三分法：


## 编程作业：模式识别

给 n 个不同的点，找出所连最长的线段，每条线段至少包括四个点。


