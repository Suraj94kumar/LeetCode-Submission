class ProductOfNumbers {

    ArrayList<Integer> arr;

    public ProductOfNumbers() {
        arr = new ArrayList<>();
        arr.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            arr.clear();
            arr.add(1);
        } else {
            int last = arr.get(arr.size() - 1);
            arr.add(last * num);
        }
    }

    public int getProduct(int k) {
        int n = arr.size();

        if (k >= n) {
            return 0;
        }

        return arr.get(n - 1) / arr.get(n - k - 1);
    }
}