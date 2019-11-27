<template>
  <user-form :is-edit="true" />
</template>

<script>
import userForm from './userForm'

export default {
  name: 'EditUser',
  components: { userForm }
}
</script>
