class Solution { public void rotate(int[] nums, int k) {
    int n = nums.length;
    k=k%n; int start=0,end=n-1;
    reverse(nums,start,end);
    reverse(nums,start,k-1); 
    reverse(nums,k,end); 
    } 
    public static void reverse(int[]nums, int start,int end){
         while(start<end){ int temp=nums[start]; 
         nums[start]=nums[end];
         nums[end]=temp; 
         start++;
         end--; 
        } 
    } 
}