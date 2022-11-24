#!/bin/bash


etcd --name etcd-node01 --initial-advertise-peer-urls http://127.0.0.1:2370 \
  --listen-peer-urls http://127.0.0.1:2370 \
  --listen-client-urls http://127.0.0.1:2369,http://127.0.0.1:2369 \
  --advertise-client-urls http://127.0.0.1:2369 \
  --initial-cluster-token etcd-cluster-1 \
  --initial-cluster etcd-node01=http://127.0.0.1:2370,etcd-node02=http://127.0.0.1:2380,etcd-node03=http://127.0.0.1:2390 \
  --initial-cluster-state new
  
  
etcd --name etcd-node02 --initial-advertise-peer-urls http://127.0.0.1:2380 \
  --listen-peer-urls http://127.0.0.1:2380 \
  --listen-client-urls http://127.0.0.1:2379,http://127.0.0.1:2379 \
  --advertise-client-urls http://127.0.0.1:2379 \
  --initial-cluster-token etcd-cluster-1 \
  --initial-cluster etcd-node01=http://127.0.0.1:2370,etcd-node02=http://127.0.0.1:2380,etcd-node03=http://127.0.0.1:2390 \
  --initial-cluster-state new
  
  
etcd --name etcd-node03 --initial-advertise-peer-urls http://127.0.0.1:2390 \
  --listen-peer-urls http://127.0.0.1:2390 \
  --listen-client-urls http://127.0.0.1:2389,http://127.0.0.1:2389 \
  --advertise-client-urls http://127.0.0.1:2389 \
  --initial-cluster-token etcd-cluster-1 \
  --initial-cluster etcd-node01=http://127.0.0.1:2370,etcd-node02=http://127.0.0.1:2380,etcd-node03=http://127.0.0.1:2390 \
  --initial-cluster-state new 
