class ChangesForMysql2 < ActiveRecord::Migration
  def change
    change_column :customers, :phone_number, :bigint
  end
end
