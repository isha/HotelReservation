class EmployeesController < ApplicationController
  def index
    @employees = Employee.all
  end

  def new
  end

  def create
    @employee = Employee.new(employee_params)
 
    @employee.save
    redirect_to @employee
  end

  def show
    @employee = Employee.find(params[:id])
  end

  def destroy
    @employee = Employee.find(params[:id])
    @employee.destroy

    redirect_to employees_path
  end

  private
  def employee_params
    params.require(:employee).permit(:name)
  end
end
