package com.rusklabo.aeroForge;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import java.util.Set;
import java.util.UUID;

public class Ship {

    private final UUID id;
    private final Player pilot;
    private final Set<Block> blocks;
    private final Location origin;
    private boolean isMoving;

    public Ship(Player pilot, Set<Block> blocks, Location origin) {
        this.id = UUID.randomUUID(); // 各乗り物に一意のIDを付与
        this.pilot = pilot;
        this.blocks = blocks;
        this.origin = origin;
        this.isMoving = false;
    }

    // ゲッターメソッド
    public UUID getId() {
        return id;
    }

    public Player getPilot() {
        return pilot;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public Location getOrigin() {
        return origin;
    }

    public boolean isMoving() {
        return isMoving;
    }

    // セッターメソッド
    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    /**
     * 乗り物を指定された方向に移動させるメソッド（スタブ）
     * このメソッド内に移動ロジックを実装します。
     * @param direction 移動方向
     */
    public void move(int dx, int dy, int dz) {
        if (isMoving) return;
        setMoving(true);
        // ここにブロックの削除・再配置ロジックを実装
        // ...
        setMoving(false);
    }
}
