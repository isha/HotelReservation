class CreateRoomTypes < ActiveRecord::Migration
  def change
    create_table :room_types, :primary_key => :type do |t|
      t.integer :security_deposit
      t.integer :daily_rate

      t.timestamps
    end
    change_column :room_types, :type, :string
  end
end
