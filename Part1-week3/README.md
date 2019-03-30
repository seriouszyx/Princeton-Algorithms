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

