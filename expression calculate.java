import java.util.Scanner;
import java.util.Stack;

public class Main 
{

	public static void cal_expression(String expression)
	{
		char [] arr = expression.toCharArray();
		Stack<Integer> value = new Stack<Integer>();
		Stack<Character> operator = new Stack<Character>();
		for(int i = 0; i < expression.length();i++)
		{
			if(arr[i] >= '0' && arr[i] <= '9')
			{
				int temp = arr[i] - '0';
				i++; //here we want to judge if the number is only one bit, if not we will generate the number by a loop.
				while(i < expression.length() && arr[i] >= '0' && arr[i] <= '9')
				{
					temp = temp * 10 + (arr[i] - '0');
					i++;
				}
				i--;
				value.push(temp);
			}
			else if(arr[i] == '-' && i == 0)//if the first number is minus, we just regard it is a special situation.
			{
				i++;
				int temp = arr[i] - '0';
				i++;
				while(i < expression.length() && arr[i] >= '0' && arr[i] <= '9')
				{
					temp = temp * 10 + (arr[i] - '0');
					i++;
				}
				i--;
				value.push(-temp);
			}
			else if(arr[i] == '-' && i > 0 && !(arr[i-1] >= '0' && arr[i-1] <= '9') && arr[i-1] != ')')//handle the minus numbers which are not the first number.
			{
				i++;
				int temp = arr[i] - '0';
				i++;
				while(i < expression.length() && arr[i] >= '0' && arr[i] <= '9')
				{
					temp = temp * 10 + (arr[i] - '0');
					i++;
				}
				i--;
				value.push(-temp);
			}
			else
			{
				if(operator.isEmpty())
					operator.push(arr[i]);
				else if(arr[i] == '(')
					operator.push(arr[i]);
				else
				{
					char last_op = operator.pop();
					if(last_op == '(' && arr[i] != ')')
					{
						operator.push(last_op);
						operator.push(arr[i]);
					}
					else
					{
						if(arr[i] == ')')
						{//solve the expression until we met '('.
							while(last_op != '(')
							{
								int a = value.pop();
								int b = value.pop();
								int result = cal_single(a, b, last_op);
								last_op = operator.pop();
								value.push(result);
							}
						}
						else
						{
							int compare_result = com_priority(last_op, arr[i]);
							if(compare_result == 0) // this means that we something wrong appear, that we cannot obtain the correct priority
								System.out.println("Something is wrong with the operator");
							else if(compare_result == 1) // this means the priority is the same or the new operator's priority is less than the last one.
							{
								int a = value.pop();
								int b = value.pop();
								int result = cal_single(a, b, last_op);
								value.push(result);
								if(operator.size() > 0)
								{
									char next_op = operator.pop();
									if(next_op == '(')
										operator.push(next_op);
									else
									{
										int temp = com_priority(next_op, arr[i]);
										while(temp == 1)//calculate the expression until the arr[i]'s priority is less.
										{
											int tempA = value.pop();
											int tempB = value.pop();
											int temp_result = cal_single(tempA, tempB, next_op);
											value.push(temp_result);
											if(operator.size() > 0)
											{
												next_op = operator.pop();
												if(next_op == '(')
												{
													operator.push(next_op);
													temp = 2;
												}
												else
													temp = com_priority(next_op, arr[i]);
											}
											else
												break;
										}
										if(temp == 2)
											operator.push(next_op);
									}
								}
								operator.push(arr[i]);
							}
							else if(compare_result == 2) // this means the new operator's priority is greater than the last one.
							{
								operator.push(last_op);
								operator.push(arr[i]);
							}
						}
					}
				}
			}
		}
		if(value.size() == 2)
		{
			int a = value.pop();
			int b = value.pop();
			char op = operator.pop();
			int result = cal_single(a, b, op);
			System.out.println(result);
		}
		else
			System.out.println(value.pop());
	}
	public static int cal_single(int a, int b, char op)
	{
		int result = 0;
		switch(op)
		{
			case '+':
				result = a + b;
				break;
			case '-':
				result = b - a;
				break;
			case '*':
				result = a * b;
				break;
			case '/':
				result = b / a;
				break;
			case '%':
				result = b % a;
				break;
			default:
				break;
		}
		return result;
	}
	public static int com_priority(char a, char b)
	{
		int result = 0;
		if(a == '+' && b == '-' || a == '+' && b == '+' || a == '-' && b == '+' || a == '-' && b == '-')
			result = 1;
		else if(a == '*' && b == '+' || a == '*' && b == '-' || a == '/' && b == '+' || a == '/' && b == '-' || a == '%' && b == '+' || a == '%' && b == '-')
			result = 1;
		else if(a == '*' && b == '*' || a == '*' && b == '/' || a == '*' && b == '%' || a == '/' && b == '*' || a == '/' && b == '/' || a == '/' && b == '%' || a == '%' && b == '*' || a == '%' && b == '/' || a == '%' && b == '%')
			result = 1;
		else if(a == '+' && b == '*' || a == '+' && b == '/' || a == '+' && b == '%' || a == '-' && b == '*' || a == '-' && b == '/' || a == '-' && b == '%')
			result = 2;
		return result;
	}
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		String expression = in.nextLine();
		cal_expression(expression);
		in.close();
	}
}
