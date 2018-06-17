def bubbleSort( myList ):
    n = len(myList)
    while True:
        swapped = False
        for i in range(1,n):
            if myList[i-1] > myList[i] :
                temp = myList[i-1]
                myList[i-1] = myList[i]
                myList[i] = temp
                swapped = True            
        n = n - 1
        if(not swapped):
            break

a = [3,6,1,3,2,6,931,4354,213,8568,2423521,86,8548]
print(a)
bubbleSort(a)
print(a)