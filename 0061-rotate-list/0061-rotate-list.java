/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {

        if (head == null || head.next == null || k == 0) {
            return head;
        }

        ListNode temp = head;
        int n = 1;

        while (temp.next != null) {
            temp = temp.next;
            n++;
        }

        temp.next = head;

        k = k % n;

        int steps = n - k;

        ListNode newTail = temp;

        while (steps > 0) {
            newTail = newTail.next;
            steps--;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }
}