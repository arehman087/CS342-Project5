Anatoly Tverdovsky - atverd2        *
Abdul Rehman - arehma7              *
CS342 Program Project 5             *
*************************************

    The location of resource file primes is in the folder labeled "res". The file "Primes"
contains the prime numbers that the program will randomly choose as needed.

    To start the program the the "SGui" must be launched first to begin the server and then
the "Main" must be launched to begin the client. Once the clients have connected themselves to 
the server each client can choose another client (excluding themelves) from a list to choose 
what messange to send. the message can be encrypted either by the user's choice of a public 
and private key or the program will randomly choose the keys from a file (Note: the values of
P and Q cannot be the same). The message is then encrypted into a large number then is sent over 
the network to the sever which it is rerouted to the client of the users choosing. The encrypted 
value is shown in the terminal while the the chat windows only show the decrypted messages.