class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();

        int left = 0;
        int right = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                left++;
            } else if (ch == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        remove(s, 0, left, right, result);

        return result;
    }

    private void remove(String s, int index, int left,
                        int right, List<String> result) {

        if (left == 0 && right == 0) {
            if (isValid(s)) {
                result.add(s);
            }
            return;
        }

        for (int i = index; i < s.length(); i++) {
            if (i != index && s.charAt(i) == s.charAt(i - 1)) {
                continue;
            }
            if (left > 0 && s.charAt(i) == '(') {
                remove(
                    s.substring(0, i) + s.substring(i + 1),
                    i,
                    left - 1,
                    right,
                    result
                );
            }
            if (right > 0 && s.charAt(i) == ')') {
                remove(
                    s.substring(0, i) + s.substring(i + 1),
                    i,
                    left,
                    right - 1,
                    result
                );
            }
        }
    }

    private boolean isValid(String s) {
        int count = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                count--;

                if (count < 0) {
                    return false;
                }
            }
        }

        return count == 0;
    }
}