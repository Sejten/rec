import sys

'''
Input
    Two numbers in two lines (n, m).
Output
    This program prints integers from n to m (inclusive), but
        ● for multiples of three, print Fizz (instead of the number)
        ● for multiples of five, print Buzz (instead of the number)
        ● for multiples of both three and five, print FizzBuzz (instead of the number)
'''
if __name__ == "__main__":
    n = int(input("n="))
    m = int(input("m="))
    # 1 <= n < m <= 10000
    if n < 1:
        print("n should be greater or equal to 1")
        sys.exit()
    if n >= m:
        print("m should be greater than n")
        sys.exit()
    if m > 10000:
        print("m should be less or equal than 10000 ")
        sys.exit()

    for i in range(n, m + 1):
        s = i
        if i % 3 == 0 and i % 5 == 0:
            s = "FizzBuzz"
        elif i % 3 == 0:
            s = "Fizz"
        elif i % 5 == 0:
            s = "Buzz"
        print(s)
