class Solution{
    public  void rotate(int[]nums,int k){
        int n=nums.length;
        k%=n;
        int i=0,j=n-1;
        reverse(nums,i,j);
        reverse(nums,i,k-1);
        reverse(nums,k,j);
    }
    public static void reverse(int[]nums,int i,int j){
        while(i<j){
            int temp=nums[i];
            nums[i]=nums[j];
            nums[j]=temp;
            i++;
            j--;
        }
    }
}