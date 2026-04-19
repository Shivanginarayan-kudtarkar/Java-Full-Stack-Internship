function runCode() {

    let output = "";

    // Q1
    let a = 10, b = 5;
    output += "Addition: " + (a+b) + "<br>";
    output += "Subtraction: " + (a-b) + "<br>";
    output += "Multiplication: " + (a*b) + "<br>";
    output += "Division: " + (a/b) + "<br><br>";

    // Q2
    output += "a > b: " + (a>b) + "<br>";
    output += "a == b: " + (a==b) + "<br>";
    output += "a != b: " + (a!=b) + "<br><br>";

    // Q3
    let age = 20, hasID = true;
    output += (age>=18 && hasID ? "Allowed" : "Not Allowed") + "<br><br>";

    // Q4
    let num = 7;
    output += (num%2==0 ? "Even" : "Odd") + "<br><br>";

    // Q5
    let marks = 85;
    if(marks>=90) output+="A<br>";
    else if(marks>=70) output+="B<br>";
    else if(marks>=50) output+="C<br>";
    else output+="Fail<br>";

    // Q6
    for(let i=1;i<=10;i++) output+= i + " ";
    output+="<br><br>";

    // Q7
    let sum=0;
    for(let i=1;i<=5;i++) sum+=i;
    output+="Sum: "+sum+"<br><br>";

    // Q8
    for(let i=1;i<=10;i++)
        output+="5 x "+i+" = "+(5*i)+"<br>";

    output+="<br>";

    // Q9
    let x=10,y=20,z=15;
    output+="Largest: "+Math.max(x,y,z)+"<br><br>";

    // Q10
    for(let i=1;i<=20;i++){
        if(i%15==0) output+="FizzBuzz ";
        else if(i%3==0) output+="Fizz ";
        else if(i%5==0) output+="Buzz ";
        else output+=i+" ";
    }

    output+="<br><br>";

    // Q11
    let username="admin", password="1234";
    output+=(username==="admin" && password==="1234") 
        ? "Login Success<br>" 
        : "Invalid Credentials<br>";

    // Q12
    let count=0;
    for(let i=1;i<=20;i++) if(i%2==0) count++;
    output+="Even Count: "+count;

    document.getElementById("output").innerHTML = output;
}