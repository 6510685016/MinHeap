package MinHeap;

// MaxHeap.java
public class MinHeap {
    private Node[] A;
    private int n;

    public MinHeap(int capacity) {
        A = new Node[capacity];
        n = 0;
    }

    // เพิ่มโหนด i ลงในตำแหน่งสุดท้ายของ Heap แล้วสลับขึ้นบน O(log n)
    public void insert(Node i) {
        int p = n++;
        while ((p != 0) && (A[parent(p)].key > i.key)) {
            A[p] = A[parent(p)];
            p = parent(p);
        }
        A[p] = i;
    }

    // สร้าง Min Heap จากอาร์เรย์ AO(n)
    private void buildMinHeap() {
        for (int i = (n / 2) - 1; i >= 0; i--) {
            minHeapify(i, n);
        }
    }

    // ลบโหนดที่มีค่าน้อยที่สุด (root) และ minHeapify O(log n)
    public Node removeMin() throws HeapEmpty {
        if (n < 1) {
            throw new HeapEmpty("Heap is empty");
        }
        Node min = A[0];
        A[0] = A[--n];
        minHeapify(0, n);
        return min;
    }

    // ปรับค่าน้อยที่สุด(ในหมู่ i และลูกๆของ i) มาอยู่ที่ตำแหน่ง i และเรียง O(log n)
    private void minHeapify(int i, int heapSize) {
        int l, r, smallest;
        l = leftChild(i);
        r = rightChild(i);

        if ((l < heapSize) && (A[l].key < A[i].key)) {
            smallest = l;
        } else {
            smallest = i;
        }

        if ((r < heapSize) && (A[r].key < A[smallest].key)) {
            smallest = r;
        }

        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest, heapSize);
        }
    }

    // ทำ MinHeap Sort
    public void heapSort() {
        buildMinHeap();
        for (int i = n - 1; i >= 1; i--) {
            swap(0, i);
            minHeapify(0, i);
        }
    }

    private void swap(int i, int j) {
        Node temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    public static void main(String[] args) {
        try {
            // สร้าง MinHeap
            MinHeap minHeap = new MinHeap(10);

            // เพิ่มโหนด
            minHeap.insert(new Node(10));
            minHeap.insert(new Node(8));
            minHeap.insert(new Node(11));
            minHeap.insert(new Node(3));

            // ทดสอบ Heap Sort
            System.out.println("Heap before sorting:");
            printHeap(minHeap);
            minHeap.heapSort();
            System.out.println("Heap after sorting:");
            printHeap(minHeap);
            minHeap.buildMinHeap(); // สร้าง Min Heap
            System.out.println("\nHeap after building Min Heap:");
            printHeap(minHeap);

            // ทดสอบการลบโหนดที่มีค่าน้อยที่สุด
            Node removedNode = minHeap.removeMin();
            System.out.println("\nRemoved node with key: " + removedNode.key);
            System.out.println("Heap after removal:");
            printHeap(minHeap);

        } catch (HeapEmpty e) {
            System.out.println("Heap is empty: " + e.getMessage());
        }
    }

    private static void printHeap(MinHeap minHeap) {
        for (int i = 0; i < minHeap.n; i++) {
            System.out.print(minHeap.A[i].key + " ");
        }
        System.out.println();
    }

}
