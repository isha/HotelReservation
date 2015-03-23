class RoomsController < ApplicationController
  def index
    @rooms = Room.all
  end
  
  def new
    @room = Room.new
  end

  def create
    @room = Room.new(room_params)

    if @room.save
      redirect_to @room
    else
      render 'new'
    end
  end

  def show
    @room = Room.find(params[:r_number])
  end


  private
  def room_params
    params.require(:room).permit(:r_number, :r_type)
  end
end
