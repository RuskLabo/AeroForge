package com.rusklabo.aeroForge;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ShipManager {

    // プレイヤーUUIDをキーに、Shipインスタンスを格納
    private final Map<UUID, Ship> activeShips = new HashMap<>();

    /**
     * 新しい乗り物を登録する
     * @param ship 登録するShipインスタンス
     */
    public void registerShip(Ship ship) {
        activeShips.put(ship.getPilot().getUniqueId(), ship);
    }

    /**
     * 乗り物を登録解除する
     * @param pilot 登録解除する乗り物の操縦者
     */
    public void unregisterShip(Player pilot) {
        activeShips.remove(pilot.getUniqueId());
    }

    /**
     * 指定されたプレイヤーが操縦している乗り物を取得する
     * @param pilot 操縦者
     * @return Shipインスタンス（存在しない場合はOptional.empty()）
     */
    public Optional<Ship> getShip(Player pilot) {
        return Optional.ofNullable(activeShips.get(pilot.getUniqueId()));
    }
}
