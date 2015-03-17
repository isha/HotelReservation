class CustomersController < ApplicationController
  def index
    @customers = Customer.all
  end

  def new
    @customer = Customer.new
  end

  def create
    @customer = Customer.new(customer_params)
 
    if @customer.save
      redirect_to @customer
    else
      render 'new'
    end
  end

  def show
    @customer = Customer.find(pk)
  end

  def edit
    @customer = Customer.find(pk)
  end

  def update
    @customer = Customer.find(pk)
 
    if @customer.update(customer_params)
      redirect_to @customer
    else
      render 'edit'
    end
  end

  def destroy
    @customer = Customer.find(pk)
    @customer.destroy

    redirect_to customers_path
  end

  private
  def customer_params
    params.require(:customer).permit(:name, :phone_number)
  end

  def pk
    matchdata = params[:id].match(/(\w+),(\w+)*/)
    [matchdata[1], matchdata[2]]
  end
end
